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
 * This is the business class test for the object Area
 */
public class AreaBusinessTest extends LuteceTestCase
{
    private static final int IDCAMPAIGN1 = 1;
    private static final int IDCAMPAIGN2 = 2;
    private static final String TITLE1 = "Title1";
    private static final String TITLE2 = "Title2";
    private static final String TYPE1 = "Type1";
    private static final String TYPE2 = "Type2";
    private static final int NUMBERVOTES1 = 1;
    private static final int NUMBERVOTES2 = 2;
    private static final boolean ACTIVE1 = true;
    private static final boolean ACTIVE2 = false;

    /**
     * test Area
     */
    public void testBusiness( )
    {
        // Initialize an object
        Area area = new Area( );
        area.setIdCampaign( IDCAMPAIGN1 );
        area.setTitle( TITLE1 );
        area.setType( TYPE1 );
        area.setNumberVotes( NUMBERVOTES1 );
        area.setActive( ACTIVE1 );

        // Create test
        AreaHome.create( area );
        Area areaStored = AreaHome.findByPrimaryKey( area.getId( ) );
        assertEquals( areaStored.getIdCampaign( ), area.getIdCampaign( ) );
        assertEquals( areaStored.getTitle( ), area.getTitle( ) );
        assertEquals( areaStored.getType( ), area.getType( ) );
        assertEquals( areaStored.getNumberVotes( ), area.getNumberVotes( ) );
        assertEquals( areaStored.getActive( ), area.getActive( ) );

        // Update test
        area.setIdCampaign( IDCAMPAIGN2 );
        area.setTitle( TITLE2 );
        area.setType( TYPE2 );
        area.setNumberVotes( NUMBERVOTES2 );
        area.setActive( ACTIVE2 );
        AreaHome.update( area );
        areaStored = AreaHome.findByPrimaryKey( area.getId( ) );
        assertEquals( areaStored.getIdCampaign( ), area.getIdCampaign( ) );
        assertEquals( areaStored.getTitle( ), area.getTitle( ) );
        assertEquals( areaStored.getType( ), area.getType( ) );
        assertEquals( areaStored.getNumberVotes( ), area.getNumberVotes( ) );
        assertEquals( areaStored.getActive( ), area.getActive( ) );

        // List test
        AreaHome.getAreasList( );

        // Delete test
        AreaHome.remove( area.getId( ) );
        areaStored = AreaHome.findByPrimaryKey( area.getId( ) );
        assertNull( areaStored );

    }

}
