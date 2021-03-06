/*
 * This file is part of the GeoLatte project.
 *
 *     GeoLatte is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     GeoLatte is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with GeoLatte.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  Copyright (C) 2010 - 2011 and Ownership of code is shared by:
 *  Qmino bvba - Esperantolaan 4 - 3001 Heverlee  (http://www.qmino.com)
 *  Geovise bvba - Generaal Eisenhowerlei 9 - 2140 Antwerpen (http://www.geovise.com)
 */

package org.geolatte.maprenderer.sld;

import com.vividsolutions.jts.geom.Geometry;
import net.opengis.se.v_1_1_0.GeometryType;
import net.opengis.se.v_1_1_0.ParameterValueType;
import net.opengis.se.v_1_1_0.SymbolizerType;
import org.geolatte.maprenderer.map.MapGraphics;
import org.geolatte.maprenderer.shape.ShapeAdapter;
import org.geolatte.maprenderer.util.JAXBHelper;

import java.awt.*;

/**
 * @author Karel Maesen, Geovise BVBA, 2010.
 *
 */
public abstract class AbstractSymbolizer {

    final private UOM uom;

    //TODO -- stroke and paint factories should be injected in constructor.
    final private StrokeFactory strokeFactory = new StrokeFactory();
    final private PaintFactory paintFactory = new PaintFactory();



    public AbstractSymbolizer(SymbolizerType type){
        if (type.getUom() != null) {
            UOM uom = UOM.fromURI(type.getUom());
            this.uom = uom;
        } else {
            uom = UOM.PIXEL;
        }
    }

    public UOM getUOM() {
        return uom;
    }

    public abstract void symbolize(MapGraphics graphics, Geometry geometry);

    protected Value<Float> readPerpendicularOffset(ParameterValueType parameterValueType){
        Value<Float> defaultOffset = Value.of(0f, UOM.PIXEL);
        String valueStr = extractParameterValue(parameterValueType);
        if (valueStr == null) {
            return defaultOffset;
        }
        return Value.of(valueStr.toString(), this.getUOM());
    }

    //TODO -- XPath expressions are not supported.
    protected String readGeometry(GeometryType geometryType){
        if (geometryType == null) return null;
        if (geometryType.getPropertyName() == null) return null;
        java.util.List<Object> list = geometryType.getPropertyName().getContent();
        return JAXBHelper.extractValueToString(list);
    }

    protected String extractParameterValue(ParameterValueType parameterValueType) {
        return SLD.instance().extractParameterValue(parameterValueType);
    }

    protected StrokeFactory getStrokeFactory(){
        return this.strokeFactory;
    }

    protected PaintFactory getPaintFactory(){
        return this.paintFactory;
    }

    protected Shape[] toShapes(MapGraphics graphics, Geometry geometry) {
        return new ShapeAdapter(graphics.getTransform()).toShape(geometry);
    }
}
