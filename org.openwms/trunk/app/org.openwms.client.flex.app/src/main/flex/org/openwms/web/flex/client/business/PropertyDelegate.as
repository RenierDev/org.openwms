/*
 * openwms.org, the Open Warehouse Management System.
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software. If not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.web.flex.client.business {

    import mx.controls.Alert;
    
    import org.as3commons.reflect.Type;
    import org.granite.tide.events.TideFaultEvent;
    import org.granite.tide.events.TideResultEvent;
    import org.granite.tide.spring.Context;
    import org.openwms.core.domain.values.Unit;
    import org.openwms.common.domain.values.Weight;
    import org.openwms.web.flex.client.model.ModelLocator;
    
    /**
     * A PropertyDelegate.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision$
     */
    [Name("propertyDelegate")]
    [ManagedEvent(name="LOAD_ALL_PROPERTIES")]
    [Bindable]
    public class PropertyDelegate {

        [In]
        public var tideContext : Context;
        [In]
        public var modelLocator : ModelLocator;

        public function PropertyDelegate() : void { }

        [Observer("LOAD_ALL_PROPERTIES")]
        public function findProperties() : void {
            tideContext.configurationService.getAllUnits(onPropertiesLoaded, onFault);
        }
        private function onPropertiesLoaded(event : TideResultEvent) : void {
        	for each (var prop:Unit in event.result) {
        		if (prop is Weight) {
        			var type:Type = Type.forInstance((prop as Weight).unit);
        			for each (var i:* in type.constants) {
        				trace("c:"+i);
        			}
        		  modelLocator.allProperties.addItem((prop as Weight).unit);
        		}
        	}
        }

        private function onFault(event : TideFaultEvent) : void {
            trace("Error accessing ConfigurationService : " + event.fault);
            Alert.show("Error accessing ConfigurationService");
        }
    }
}