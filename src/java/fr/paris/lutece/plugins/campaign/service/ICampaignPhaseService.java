package fr.paris.lutece.plugins.campaign.service;

import java.util.Collection;

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

    /**
     * @param campaign
     *          code
     * @return The phase collection
     */
	public Collection<Phase> getPhasesListByCampaign(String campagneCode); 
   
}
