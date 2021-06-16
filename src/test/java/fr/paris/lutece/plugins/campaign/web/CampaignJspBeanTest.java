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
import fr.paris.lutece.plugins.campaign.business.CampaignHome;

/**
 * This is the business class test for the object Campaign
 */
public class CampaignJspBeanTest extends LuteceTestCase
{
    private static final String CODECAMPAIGN1 = "CodeCampaign1";
    private static final String CODECAMPAIGN2 = "CodeCampaign2";
    private static final String TITLE1 = "Title1";
    private static final String TITLE2 = "Title2";
    private static final String DESCRIPTION1 = "Description1";
    private static final String DESCRIPTION2 = "Description2";
    private static final boolean ACTIVE1 = true;
    private static final boolean ACTIVE2 = false;

    public void testJspBeans( ) throws AccessDeniedException
    {
        MockHttpServletRequest request = new MockHttpServletRequest( );
        MockHttpServletResponse response = new MockHttpServletResponse( );
        MockServletConfig config = new MockServletConfig( );

        // display admin Campaign management JSP
        CampaignJspBean jspbean = new CampaignJspBean( );
        String html = jspbean.getManageCampaigns( request );
        assertNotNull( html );

        // display admin Campaign creation JSP
        html = jspbean.getCreateCampaign( request );
        assertNotNull( html );

        // action create Campaign
        request = new MockHttpServletRequest( );

        request.addParameter( "code_campaign", CODECAMPAIGN1 );
        request.addParameter( "title", TITLE1 );
        request.addParameter( "description", DESCRIPTION1 );
        request.addParameter( "active", String.valueOf( ACTIVE1 ) );
        request.addParameter( "action", "createCampaign" );
        request.addParameter( "token", SecurityTokenService.getInstance( ).getToken( request, "createCampaign" ) );
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

        // display modify Campaign JSP
        request = new MockHttpServletRequest( );
        request.addParameter( "code_campaign", CODECAMPAIGN1 );
        request.addParameter( "title", TITLE1 );
        request.addParameter( "description", DESCRIPTION1 );
        request.addParameter( "active", String.valueOf( ACTIVE1 ) );
        List<Integer> listIds = CampaignHome.getIdCampaignsList( );
        assertTrue( !listIds.isEmpty( ) );
        request.addParameter( "id", String.valueOf( listIds.get( 0 ) ) );
        jspbean = new CampaignJspBean( );

        assertNotNull( jspbean.getModifyCampaign( request ) );

        // action modify Campaign
        request = new MockHttpServletRequest( );
        response = new MockHttpServletResponse( );
        request.addParameter( "code_campaign", CODECAMPAIGN2 );
        request.addParameter( "title", TITLE2 );
        request.addParameter( "description", DESCRIPTION2 );
        request.addParameter( "active", String.valueOf( ACTIVE2 ) );
        request.setRequestURI( "jsp/admin/plugins/example/ManageCampaigns.jsp" );
        // important pour que MVCController sache quelle action effectuer, sinon, il redirigera vers createCampaign, qui est l'action par défaut
        request.addParameter( "action", "modifyCampaign" );
        request.addParameter( "token", SecurityTokenService.getInstance( ).getToken( request, "modifyCampaign" ) );
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

        // get remove Campaign
        request = new MockHttpServletRequest( );
        // request.setRequestURI("jsp/admin/plugins/example/ManageCampaigns.jsp");
        request.addParameter( "id", String.valueOf( listIds.get( 0 ) ) );
        jspbean = new CampaignJspBean( );
        request.addParameter( "action", "confirmRemoveCampaign" );
        assertNotNull( jspbean.getModifyCampaign( request ) );

        // do remove Campaign
        request = new MockHttpServletRequest( );
        response = new MockHttpServletResponse( );
        request.setRequestURI( "jsp/admin/plugins/example/ManageCampaignts.jsp" );
        // important pour que MVCController sache quelle action effectuer, sinon, il redirigera vers createCampaign, qui est l'action par défaut
        request.addParameter( "action", "removeCampaign" );
        request.addParameter( "token", SecurityTokenService.getInstance( ).getToken( request, "removeCampaign" ) );
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
