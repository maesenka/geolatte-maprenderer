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

import net.opengis.se.v_1_1_0.FeatureTypeStyleType;
import org.geolatte.geom.Envelope;
import org.geolatte.geom.crs.CrsId;
import org.geolatte.maprenderer.java2D.JAIMapGraphics;
import org.geolatte.maprenderer.map.MapGraphics;
import org.junit.BeforeClass;

import java.io.InputStream;

public class BaseFeatureTypeStyleTest {

    static FeatureTypeStyleType sldRoot;
    FeatureTypeStyle featureTypeStyle;

    public MapGraphics createMapGraphics() {
        return createMapGraphics(100, 10000);
    }

    public MapGraphics createMapGraphics(int pixelSize, double extentSize) {
        Envelope extent = new Envelope(0, 0, extentSize, extentSize, new CrsId("EPSG",31370));
        java.awt.Dimension dim = new java.awt.Dimension(pixelSize, pixelSize);
        MapGraphics mapGraphics =  new JAIMapGraphics(dim, extent);
        return mapGraphics;
    }



    @BeforeClass
    public static void beforeClass() {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("test-sld.xml");
        sldRoot = SLD.instance().unmarshal(in);
    }

    public void before() {
        featureTypeStyle = new FeatureTypeStyle(sldRoot);
    }

    /**
     * Returns the FeatureTypeStyle created form test-sld.xml
     */
    public FeatureTypeStyle getFeatureTypeStyle() {
        return featureTypeStyle;
    }


}
