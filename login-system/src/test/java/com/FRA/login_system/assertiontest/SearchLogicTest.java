package com.FRA.login_system.assertiontest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// Import all entities used for assertions
import com.FRA.login_system.entity.UserAccount;
import com.FRA.login_system.entity.UserProfile;
import com.FRA.login_system.entity.Activity;
import com.FRA.login_system.entity.FRA;
import com.FRA.login_system.entity.Listing;
import com.FRA.login_system.entity.FSACategory;
import com.FRA.login_system.entity.SavedListing;
import com.FRA.login_system.entity.Donation;

// Import all distinct search service layers
import com.FRA.login_system.service.UserAccountService.SearchUserAccountService;
import com.FRA.login_system.service.UserProfileService.SearchUserProfileService;
import com.FRA.login_system.service.ActivityService.SearchActivityService;
import com.FRA.login_system.service.FRAService.SearchCompletedFRAService;
import com.FRA.login_system.service.DonationService.SearchDonationHistoryService;
import com.FRA.login_system.service.ListingService.SearchListingsService;
import com.FRA.login_system.service.ListingService.SearchSavedListingsService;
import com.FRA.login_system.service.FSACategoryService.SearchFSACategoryService;

@SpringBootTest
public class SearchLogicTest {

    @Autowired
    private SearchUserAccountService searchAccountService;

    @Autowired
    private SearchUserProfileService searchProfileService;

    @Autowired
    private SearchActivityService searchActivityService;

    @Autowired
    private SearchCompletedFRAService searchCompletedFRAService;

    @Autowired
    private SearchDonationHistoryService searchDonationHistoryService;

    @Autowired
    private SearchListingsService searchListingsService;

    @Autowired
    private SearchSavedListingsService searchSavedListingsService;

    @Autowired
    private SearchFSACategoryService searchFSACategoryService;

    /**
     * Service Check 1: User Account Search Layer Validation
     */
    @Test
    public void testUserAccountSearchService() {
        // Test primary query logic (case-insensitive test on static record 'admin')
        List<UserAccount> results = searchAccountService.searchUser("ADMIN");
        assertNotNull(results, "User account search payload container must not be null");
        assertFalse(results.isEmpty(), "Should locate at least one user matching 'admin'");
        assertEquals("admin", results.get(0).getUsername());
    }

    /**
     * Service Check 2: User Profile Search Layer Validation
     */
    @Test
    public void testUserProfileSearchService() {
        // Test role properties search query ('FR' profile role)
        List<UserProfile> results = searchProfileService.searchUser("FR");
        assertNotNull(results, "User profile search payload container must not be null");
        assertFalse(results.isEmpty(), "Should locate the Fund Raiser (FR) profile record");
        assertEquals("FR", results.get(0).getRole());
    }

    /**
     * Service Check 3: Activity Search Layer Validation
     */
    @Test
    public void testActivitySearchService() {
        // Test filtration combination matching bulk insert parameters ('Campaign 1', 'Environment', 'Active')
        List<Activity> results = searchActivityService.searchActivity("Campaign 1", "Environment", "Active");
        assertNotNull(results, "Activity search payload container must not be null");
        
        // Ensure that if rows match, fields are structured cleanly
        for (Activity activity : results) {
            assertTrue(activity.getActivityName().contains("Campaign 1"));
            assertEquals("Environment", activity.getCategory());
            assertEquals("Active", activity.getStatus());
        }
    }

    /**
     * Service Check 4: Completed FRA Campaign Search Layer Validation
     */
    @Test
    public void testCompletedFRASearchService() {
        // Set dynamic date parameters tracking yesterday's seed index calculation boundaries
        LocalDate targetDate = LocalDate.now().minusDays(1);
        
        List<FRA> results = searchCompletedFRAService.searchCompletedFRA("Medical", targetDate);
        assertNotNull(results, "Completed FRA search payload container must not be null");
        
        for (FRA fra : results) {
            assertEquals("Completed", fra.getStatus(), "Search target filters must exclusively return Completed status items");
            assertTrue(fra.getCategory().equalsIgnoreCase("Medical"), "Returned values should match the Category requested");
        }
    }

    /**
     * Service Check 5: Filtered Donation History Search Layer Validation
     */
    @Test
    public void testDonationHistorySearchService() {
        // Bulk database script offsets dates sequentially backwards using 2-hour interval steps
        LocalDate today = LocalDate.now();
        
        var results = searchDonationHistoryService.getFilteredDonationHistory("Education", today);
        assertNotNull(results, "Donation history response list tracking container must not be null");
        
        for (Donation donation : results) {
            assertTrue(donation.getCategory().equalsIgnoreCase("Education"), "Donation log query must isolate requested Education category entries");
        }
    }

    /**
     * Service Check 6: Core Listings Search Layer Validation
     */
    @Test
    public void testListingsSearchService() {
        // Query keywords matching text payload inputs from listings script ('Help')
        List<Listing> results = searchListingsService.searchListings("Help");
        assertNotNull(results, "Listings search payload container must not be null");
        assertFalse(results.isEmpty(), "Should return matching listing items from the seed database matrix execution");
        
        assertTrue(results.get(0).getTitle().toLowerCase().contains("help") || 
                   results.get(0).getDescription().toLowerCase().contains("help"));
    }

    /**
     * Service Check 7: Saved Listings Management Verification
     */
    @Test
    public void testSavedListingsSearchAndDetails() {
        // Test matching ID criteria search logic (Assuming record ID 1 exists in context profiles)
        String targetListingId = "1";
        List<SavedListing> searchResults = searchSavedListingsService.searchSavedListings(targetListingId);
        assertNotNull(searchResults, "Saved listings tracking array context must not build as null");
        
        // Check structural details conversion formatting output text string rules
        String detailsResult = searchSavedListingsService.getListingDetails(9999);
        assertEquals("Saved listing not found.", detailsResult, "Fallback sequence must activate clean non-matching ID boundary errors");
    }

    /**
     * Service Check 8: Rotating Core FSA Category Filter Search Validation
     */
    @Test
    public void testFSACategorySearchService() {
        // Look up the rotating values provided by the unique index iteration script ('Charity')
        List<FSACategory> results = searchFSACategoryService.searchFSACategory("Charity");
        assertNotNull(results, "FSA Category query search framework payload must not load null");
        assertFalse(results.isEmpty(), "Should locate rotating text inputs generated up to index 100 loops");
        
        assertTrue(results.get(0).getCatName().toLowerCase().contains("charity") || 
                   results.get(0).getCatDesc().toLowerCase().contains("charity"));
    }
}