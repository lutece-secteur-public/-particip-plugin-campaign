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
 * This is the business class test for the object Campaign
 */
public class CampaignBusinessTest extends LuteceTestCase
{
    private static final String CODECAMPAIGN1 = "CodeCampaign1";
    private static final String CODECAMPAIGN2 = "CodeCampaign2";
    private static final String TITLE1 = "Title1";
    private static final String TITLE2 = "Title2";
    private static final String DESCRIPTION1 = "Description1";
    private static final String DESCRIPTION2 = "Description2";
    private static final boolean ACTIVE1 = true;
    private static final boolean ACTIVE2 = false;

    /**
     * test Campaign
     */
    public void testBusiness( )
    {
        // Initialize an object
        Campaign campaign = new Campaign( );
        campaign.setCodeCampaign( CODECAMPAIGN1 );
        campaign.setTitle( TITLE1 );
        campaign.setDescription( DESCRIPTION1 );
        campaign.setActive( ACTIVE1 );

        // Create test
        CampaignHome.create( campaign );
        Campaign campaignStored = CampaignHome.findByPrimaryKey( campaign.getId( ) );
        assertEquals( campaignStored.getCodeCampaign( ), campaign.getCodeCampaign( ) );
        assertEquals( campaignStored.getTitle( ), campaign.getTitle( ) );
        assertEquals( campaignStored.getDescription( ), campaign.getDescription( ) );
        assertEquals( campaignStored.getActive( ), campaign.getActive( ) );

        // Update test
        campaign.setCodeCampaign( CODECAMPAIGN2 );
        campaign.setTitle( TITLE2 );
        campaign.setDescription( DESCRIPTION2 );
        campaign.setActive( ACTIVE2 );
        CampaignHome.update( campaign );
        campaignStored = CampaignHome.findByPrimaryKey( campaign.getId( ) );
        assertEquals( campaignStored.getCodeCampaign( ), campaign.getCodeCampaign( ) );
        assertEquals( campaignStored.getTitle( ), campaign.getTitle( ) );
        assertEquals( campaignStored.getDescription( ), campaign.getDescription( ) );
        assertEquals( campaignStored.getActive( ), campaign.getActive( ) );

        // List test
        CampaignHome.getCampaignsList( );

        // Delete test
        CampaignHome.remove( campaign.getId( ) );
        campaignStored = CampaignHome.findByPrimaryKey( campaign.getId( ) );
        assertNull( campaignStored );

    }

}
