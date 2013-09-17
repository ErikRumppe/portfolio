import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler
import javax.xml.parsers.SAXParserFactory

includeTargets << grailsScript('_GrailsBootstrap')

target(importItunesLibrary: 'Import iTunes Library') {
    depends classpath, checkVersion, bootstrap

    def file = new File('C:/Users/Erik/Music/iTunes/iTunes Music Library.xml')

    if (args) {
        file = new File(args.replaceAll(/\n/, ' ').trim())
    } else {
        if (file.exists()) {
            println "Loading XML, this can take a while..."
            def parser = SAXParserFactory.newInstance().newSAXParser()
            def handler = new SAXItunesParser()
            parser.parse(file, handler)
            println "Finished reading XML, importing to app.."

            def artistClass = classLoader.loadClass('com.erumppe.portfolio.Artist')
            def albumClass = classLoader.loadClass('com.erumppe.portfolio.Album')
            def songClass = classLoader.loadClass('com.erumppe.portfolio.Song')
            def artists = handler.artistsToAlbumsToTracks

            artists.each { artistName, albums ->

                if (!artistName) {
                    artistName = "Added As Unknown"
                }

                artistClass.withTransaction { tx ->
                    def artist = artistClass.findByName(artistName)

                    if (!artist)
                        artist = artistClass.newInstance(name: artistName).save(failOnError: true)

                    albums.each { albumTitle, albumDetails ->
                        def album = albumClass.findByTitleAndYearAndGenre(albumTitle, albumDetails.year, albumDetails.genre)

                        if (!album) {
                            album = albumClass.newInstance(title: albumTitle,
                                    year: albumDetails.year ?: 2012,
                                    genre: albumDetails.genre ?: 'Unknown')
                        }

                        artist.addToAlbums album
                        album.save(failOnError: true)
                        artist.save(failOnError: true)

                        albumDetails.tracks?.each { trackInfo ->
                            def song = songClass.newInstance(title: trackInfo.name,
                                    genre: trackInfo.genre ?: 'Unknown',
                                    year: trackInfo.year ?: 2012,
                                    trackNumber: trackInfo.tracknumber ?: 1,
                                    duration: trackInfo.totaltime ?: 0)

                            def trackArtist = artistClass.newInstance(name: trackInfo.albumartist ?: "Added As Unknown")

                            if (artistClass.find(trackArtist))
                                trackArtist = artistClass.find(trackArtist)
                            else
                                trackArtist.save(failOnError: true)

                            song.artist = trackArtist

                            /* Still need to check if a song belongs to:
                                 an artist already
                                 an album already
                               If so, only addTo for which one it needs to be
                               added to.
                             */
                            if (!songClass.findByTitleAndGenreAndYearAndTrackNumberAndDuration(song.title,
                            song.genre, song.year, song.trackNumber, song.duration)) {
                            trackArtist.addToSongs(song)
                            album.addToSongs(song)
                            song.save(failOnError: true)
                            album.save(failOnError: true)
                            trackArtist.save(failOnError: true)
                            }
                        }
                    }
                }
            }
        } else {
            println "File ${args} does not exist"
        }
    }
}

setDefaultTarget importItunesLibrary


class SAXItunesParser extends DefaultHandler {

    private dictLevel = 0
    private valueOfLastKeyParsed
    private trackDetails = [:]
    private currentlyParsingTracks = false
    private elementBeingParsed

    def artistsToAlbumsToTracks = [:].withDefault {
        [:].withDefault {
            [:].withDefault {
                key ->
                    if ('tracks' == key) {
                        return []
                    } else {
                        return null
                    }
            }
        }
    }

    void startElement(String ns, String localName, String qName, Attributes atts) {
        elementBeingParsed = qName
        switch (qName) {
            case 'dict':
                dictLevel++
                if (dictLevel == 2 && 'Tracks' == valueOfLastKeyParsed) {
                    currentlyParsingTracks = true
                }
                break
        }
    }

    void characters(char[] chars, int offset, int length) {
        def str = new String(chars, offset, length)
        if ('key' == elementBeingParsed) {
            valueOfLastKeyParsed = str
        } else if (currentlyParsingTracks) {
            switch (valueOfLastKeyParsed) {
                case 'Genre':
                    trackDetails.genre = append(trackDetails.genre, str)
                    break
                case 'Artist':
                    trackDetails.albumartist = append(trackDetails.albumartist, str)
                    break
                case 'Album Artist':
                    trackDetails.artist = append(trackDetails.artist, str)
                    break
                case 'Album':
                    trackDetails.album = append(trackDetails.album, str)
                    break
                case 'Name':
                    trackDetails.name = append(trackDetails.name, str)
                    break
                case 'Year':
                    trackDetails.year = str.toInteger()
                    break
                case 'Bit Rate':
                    trackDetails.bitrate = str.toInteger()
                    break
                case 'Total Time':
                    trackDetails.totaltime = str.toInteger()
                    break
                case 'Track Number':
                    trackDetails.tracknumber = str.toInteger()
                    break
            }
        }
    }

    private append(a, b) {
        (a ?: '') + b
    }

    void endElement(String ns, String localName, String qName) {
        switch (qName) {
            case 'dict':
                dictLevel--
                if (currentlyParsingTracks && dictLevel == 2 && !trackDetails.containsKey('isVideo')) {
                    def albums = artistsToAlbumsToTracks.get(trackDetails.artist)
                    if (trackDetails.album != null) {
                        def album = albums.get(trackDetails.album)
                        if (trackDetails.containsKey('year')) {
                            album.year = trackDetails.'year'
                        }
                        if (trackDetails.containsKey('genre')) {
                            album.genre = trackDetails.genre
                        }
                        trackDetails.remove 'artist'
                        trackDetails.remove 'album'
                        album.tracks << trackDetails
                    }
                }
                trackDetails = [:]
                if (dictLevel == 1) currentlyParsingTracks = false
                break
            case 'true':
                if (valueOfLastKeyParsed in ['TV Show', 'Has Video']) {
                    trackDetails.isVideo = true
                }
        }
    }
}