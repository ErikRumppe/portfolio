/* Copyright 2011-2012 the original author or authors:
 *
 *    Marc Palmer (marc@grailsrocks.com)
 *    Stéphane Maldini (smaldini@vmware.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.grails.plugin.platform.events.utils;

import org.springframework.aop.TargetClassAware;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;

/**
 * Created with IntelliJ IDEA.
 * User: smaldini
 * Date: 8/3/12
 * Time: 3:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventsUtils {

    static public Object unproxy(Object candidate) {

        if (candidate instanceof Advised) {
            try {
                return ((Advised) candidate).getTargetSource().getTarget();
            } catch (Exception e) {
            }
        }
        return candidate;
    }
}
