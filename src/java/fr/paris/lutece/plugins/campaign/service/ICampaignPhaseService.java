package fr.paris.lutece.plugins.campaign.service;

import fr.paris.lutece.plugins.campaign.business.Phase;

public interface ICampaignPhaseService {

	 /**
     * @param campaign
     *          code
     * @param phase type
     *            code 
     * @return The phase object
     */
    public Phase findPhaseByCampaignAndPhaseTypeCode( String campaignCode, String phaseTypeCode ); 
   
}
