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
package fr.paris.lutece.plugins.campaign.web;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.service.admin.AdminAuthenticationService;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import java.util.List;
import fr.paris.lutece.test.LuteceTestCase;
import fr.paris.lutece.portal.service.security.SecurityTokenService;
import fr.paris.lutece.portal.web.LocalVariables;
import java.io.IOException;
import fr.paris.lutece.plugins.campaign.business.Phase;
import fr.paris.lutece.plugins.campaign.business.PhaseHome;
import java.sql.Date;
import fr.paris.lutece.util.date.DateUtil;
import fr.paris.lutece.portal.web.l10n.LocaleService;

/**
 * This is the business class test for the object Phase
 */
public class PhaseJspBeanTest extends LuteceTestCase
{
    private static final int IDCAMPAIGN1 = 1;
    private static final int IDCAMPAIGN2 = 2;
    private static final Date STARTINGDATE1 = new Date( 1000000l );
    private static final Date STARTINGDATE2 = new Date( 2000000l );
    private static final Date ENDINGDATE1 = new Date( 1000000l );
    private static final Date ENDINGDATE2 = new Date( 2000000l );
    private static final String LABEL1 = "Label1";
    private static final String LABEL2 = "Label2";
    private static final int ORDERNUM1 = 1;
    private static final int ORDERNUM2 = 2;

    public void testJspBeans( ) throws AccessDeniedException
    {
        MockHttpServletRequest request = new MockHttpServletRequest( );
        MockHttpServletResponse response = new MockHttpServletResponse( );
        MockServletConfig config = new MockServletConfig( );

        // display admin Phase management JSP
        PhaseJspBean jspbean = new PhaseJspBean( );
        String html = jspbean.getManagePhases( request );
        assertNotNull( html );

        // display admin Phase creation JSP
        html = jspbean.getCreatePhase( request );
        assertNotNull( html );

        // action create Phase
        request = new MockHttpServletRequest( );

        request.addParameter( "id_campaign", String.valueOf( IDCAMPAIGN1 ) );
        request.addParameter( "starting_date", DateUtil.getDateString( STARTINGDATE1, LocaleService.getDefault( ) ) );
        request.addParameter( "ending_date", DateUtil.getDateString( ENDINGDATE1, LocaleService.getDefault( ) ) );
        request.addParameter( "label", LABEL1 );
        request.addParameter( "order_num", String.valueOf( ORDERNUM1 ) );
        request.addParameter( "action", "createPhase" );
        request.addParameter( "token", SecurityTokenService.getInstance( ).getToken( request, "createPhase" ) );
        request.setMethod( "POST" );
        response = new MockHttpServletResponse( );
        AdminUser adminUser = new AdminUser( );
        adminUser.setAccessCode( "admin" );

        try
        {
            AdminAuthenticationService.getInstance( ).registerUser( request, adminUser );
            html = jspbean.processController( request, response );

            // MockResponse object does not redirect, result is always null
            assertNull( html );
        }
        catch( AccessDeniedException e )
        {
            fail( "access denied" );
        }
        catch( UserNotSignedException e )
        {
            fail( "user not signed in" );
        }

        // display modify Phase JSP
        request = new MockHttpServletRequest( );
        request.addParameter( "id_campaign", String.valueOf( IDCAMPAIGN1 ) );
        request.addParameter( "starting_date", DateUtil.getDateString( STARTINGDATE1, LocaleService.getDefault( ) ) );
        request.addParameter( "ending_date", DateUtil.getDateString( ENDINGDATE1, LocaleService.getDefault( ) ) );
        request.addParameter( "label", LABEL1 );
        request.addParameter( "order_num", String.valueOf( ORDERNUM1 ) );
        List<Integer> listIds = PhaseHome.getIdPhasesList( );
        assertTrue( !listIds.isEmpty( ) );
        request.addParameter( "id", String.valueOf( listIds.get( 0 ) ) );
        jspbean = new PhaseJspBean( );

        assertNotNull( jspbean.getModifyPhase( request ) );

        // action modify Phase
        request = new MockHttpServletRequest( );
        response = new MockHttpServletResponse( );
        request.addParameter( "id_campaign", String.valueOf( IDCAMPAIGN2 ) );
        request.addParameter( "starting_date", DateUtil.getDateString( STARTINGDATE2, LocaleService.getDefault( ) ) );
        request.addParameter( "ending_date", DateUtil.getDateString( ENDINGDATE2, LocaleService.getDefault( ) ) );
        request.addParameter( "label", LABEL2 );
        request.addParameter( "order_num", String.valueOf( ORDERNUM2 ) );
        request.setRequestURI( "jsp/admin/plugins/example/ManagePhases.jsp" );
        // important pour que MVCController sache quelle action effectuer, sinon, il redirigera vers createPhase, qui est l'action par défaut
        request.addParameter( "action", "modifyPhase" );
        request.addParameter( "token", SecurityTokenService.getInstance( ).getToken( request, "modifyPhase" ) );
        adminUser = new AdminUser( );
        adminUser.setAccessCode( "admin" );

        try
        {
            AdminAuthenticationService.getInstance( ).registerUser( request, adminUser );
            html = jspbean.processController( request, response );

            // MockResponse object does not redirect, result is always null
            assertNull( html );
        }
        catch( AccessDeniedException e )
        {
            fail( "access denied" );
        }
        catch( UserNotSignedException e )
        {
            fail( "user not signed in" );
        }

        // get remove Phase
        request = new MockHttpServletRequest( );
        // request.setRequestURI("jsp/admin/plugins/example/ManagePhases.jsp");
        request.addParameter( "id", String.valueOf( listIds.get( 0 ) ) );
        jspbean = new PhaseJspBean( );
        request.addParameter( "action", "confirmRemovePhase" );
        assertNotNull( jspbean.getModifyPhase( request ) );

        // do remove Phase
        request = new MockHttpServletRequest( );
        response = new MockHttpServletResponse( );
        request.setRequestURI( "jsp/admin/plugins/example/ManagePhasets.jsp" );
        // important pour que MVCController sache quelle action effectuer, sinon, il redirigera vers createPhase, qui est l'action par défaut
        request.addParameter( "action", "removePhase" );
        request.addParameter( "token", SecurityTokenService.getInstance( ).getToken( request, "removePhase" ) );
        request.addParameter( "id", String.valueOf( listIds.get( 0 ) ) );
        request.setMethod( "POST" );
        adminUser = new AdminUser( );
        adminUser.setAccessCode( "admin" );

        try
        {
            AdminAuthenticationService.getInstance( ).registerUser( request, adminUser );
            html = jspbean.processController( request, response );

            // MockResponse object does not redirect, result is always null
            assertNull( html );
        }
        catch( AccessDeniedException e )
        {
            fail( "access denied" );
        }
        catch( UserNotSignedException e )
        {
            fail( "user not signed in" );
        }

    }
}
