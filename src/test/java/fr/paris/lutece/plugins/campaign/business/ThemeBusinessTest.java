/*
 * Copyright (c) 2002-2021, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.campaign.business;

import fr.paris.lutece.test.LuteceTestCase;

/**
 * This is the business class test for the object Theme
 */
public class ThemeBusinessTest extends LuteceTestCase
{
    private static final int IDCAMPAIGN1 = 1;
    private static final int IDCAMPAIGN2 = 2;
    private static final String TITLE1 = "Title1";
    private static final String TITLE2 = "Title2";
    private static final String DESCRIPTION1 = "Description1";
    private static final String DESCRIPTION2 = "Description2";
    private static final boolean ACTIVE1 = true;
    private static final boolean ACTIVE2 = false;
    private static final String FRONTRGB1 = "FrontRgb1";
    private static final String FRONTRGB2 = "FrontRgb2";
    private static final int IMAGEFILE1 = 1;
    private static final int IMAGEFILE2 = 2;

    /**
     * test Theme
     */
    public void testBusiness( )
    {
        // Initialize an object
        Theme theme = new Theme( );
        theme.setIdCampaign( IDCAMPAIGN1 );
        theme.setTitle( TITLE1 );
        theme.setDescription( DESCRIPTION1 );
        theme.setActive( ACTIVE1 );
        theme.setFrontRgb( FRONTRGB1 );
        theme.setImageFile( IMAGEFILE1 );

        // Create test
        ThemeHome.create( theme );
        Theme themeStored = ThemeHome.findByPrimaryKey( theme.getId( ) );
        assertEquals( themeStored.getIdCampaign( ), theme.getIdCampaign( ) );
        assertEquals( themeStored.getTitle( ), theme.getTitle( ) );
        assertEquals( themeStored.getDescription( ), theme.getDescription( ) );
        assertEquals( themeStored.getActive( ), theme.getActive( ) );
        assertEquals( themeStored.getFrontRgb( ), theme.getFrontRgb( ) );
        assertEquals( themeStored.getImageFile( ), theme.getImageFile( ) );

        // Update test
        theme.setIdCampaign( IDCAMPAIGN2 );
        theme.setTitle( TITLE2 );
        theme.setDescription( DESCRIPTION2 );
        theme.setActive( ACTIVE2 );
        theme.setFrontRgb( FRONTRGB2 );
        theme.setImageFile( IMAGEFILE2 );
        ThemeHome.update( theme );
        themeStored = ThemeHome.findByPrimaryKey( theme.getId( ) );
        assertEquals( themeStored.getIdCampaign( ), theme.getIdCampaign( ) );
        assertEquals( themeStored.getTitle( ), theme.getTitle( ) );
        assertEquals( themeStored.getDescription( ), theme.getDescription( ) );
        assertEquals( themeStored.getActive( ), theme.getActive( ) );
        assertEquals( themeStored.getFrontRgb( ), theme.getFrontRgb( ) );
        assertEquals( themeStored.getImageFile( ), theme.getImageFile( ) );

        // List test
        ThemeHome.getThemesList( );

        // Delete test
        ThemeHome.remove( theme.getId( ) );
        themeStored = ThemeHome.findByPrimaryKey( theme.getId( ) );
        assertNull( themeStored );

    }

}
