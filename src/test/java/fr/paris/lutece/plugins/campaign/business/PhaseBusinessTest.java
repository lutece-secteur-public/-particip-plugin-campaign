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

import java.sql.Date;
import java.time.LocalDateTime;

/**
 * This is the business class test for the object Phase
 */
public class PhaseBusinessTest extends LuteceTestCase
{
    private static final String CODECAMPAIGN1 = "A";
    private static final String CODECAMPAIGN2 = "B";
    private static final LocalDateTime STARTINGDATE1 =  LocalDateTime.of(2021, 6, 16, 12, 00);
    private static final LocalDateTime STARTINGDATE2 = LocalDateTime.of(2022, 6, 16, 12, 00);
    private static final LocalDateTime ENDINGDATE1 = LocalDateTime.of(2021, 9, 16, 12, 00);
    private static final LocalDateTime ENDINGDATE2 = LocalDateTime.of(2022, 9, 16, 12, 00);
    private static final String LABEL1 = "Label1";
    private static final String LABEL2 = "Label2";
    private static final int ORDERNUM1 = 1;
    private static final int ORDERNUM2 = 2;

    /**
     * test Phase
     */
    public void testBusiness( )
    {
        // Initialize an object
        Phase phase = new Phase( );
        phase.setCampaignCode( CODECAMPAIGN1 );
        phase.setStartingDate( STARTINGDATE1 );
        phase.setEndingDate( ENDINGDATE1 );
        phase.setLabel( LABEL1 );
        phase.setOrderNum( ORDERNUM1 );

        // Create test
        PhaseHome.create( phase );
        Phase phaseStored = PhaseHome.findByPrimaryKey( phase.getId( ) );
        assertEquals( phaseStored.getCampaignCode( ), phase.getCampaignCode( ) );
        assertEquals( phaseStored.getStartingDate( ).toString( ), phase.getStartingDate( ).toString( ) );
        assertEquals( phaseStored.getEndingDate( ).toString( ), phase.getEndingDate( ).toString( ) );
        assertEquals( phaseStored.getLabel( ), phase.getLabel( ) );
        assertEquals( phaseStored.getOrderNum( ), phase.getOrderNum( ) );

        // Update test
        phase.setCampaignCode( CODECAMPAIGN2 );
        phase.setStartingDate( STARTINGDATE2 );
        phase.setEndingDate( ENDINGDATE2 );
        phase.setLabel( LABEL2 );
        phase.setOrderNum( ORDERNUM2 );
        PhaseHome.update( phase );
        phaseStored = PhaseHome.findByPrimaryKey( phase.getId( ) );
        assertEquals( phaseStored.getCampaignCode( ), phase.getCampaignCode( ) );
        assertEquals( phaseStored.getStartingDate( ).toString( ), phase.getStartingDate( ).toString( ) );
        assertEquals( phaseStored.getEndingDate( ).toString( ), phase.getEndingDate( ).toString( ) );
        assertEquals( phaseStored.getLabel( ), phase.getLabel( ) );
        assertEquals( phaseStored.getOrderNum( ), phase.getOrderNum( ) );

        // List test
        PhaseHome.getPhasesList( );

        // Delete test
        PhaseHome.remove( phase.getId( ) );
        phaseStored = PhaseHome.findByPrimaryKey( phase.getId( ) );
        assertNull( phaseStored );

    }

}
