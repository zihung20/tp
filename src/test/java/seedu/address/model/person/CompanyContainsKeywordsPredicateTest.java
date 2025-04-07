package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class CompanyContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Alpha");
        List<String> secondPredicateKeywordList = Arrays.asList("Alpha", "Beta");

        CompanyContainsKeywordsPredicate firstPredicate =
                new CompanyContainsKeywordsPredicate(firstPredicateKeywordList);
        CompanyContainsKeywordsPredicate secondPredicate =
                new CompanyContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CompanyContainsKeywordsPredicate firstPredicateCopy =
                new CompanyContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_companyContainsKeywords_returnsTrue() {
        // One keyword
        CompanyContainsKeywordsPredicate predicate =
                new CompanyContainsKeywordsPredicate(Collections.singletonList("Alpha"));
        assertTrue(predicate.test(new PersonBuilder().withCompany("Alpha").build()));

        // Multiple keywords, one match
        predicate = new CompanyContainsKeywordsPredicate(Arrays.asList("Alpha", "Bravo"));
        assertTrue(predicate.test(new PersonBuilder().withCompany("Alpha").build()));
        assertTrue(predicate.test(new PersonBuilder().withCompany("Bravo").build()));

        // Mixed-case keywords
        predicate = new CompanyContainsKeywordsPredicate(Arrays.asList("aLPha", "bRAVO"));
        assertTrue(predicate.test(new PersonBuilder().withCompany("Bravo").build()));
    }

    @Test
    public void test_companyDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        CompanyContainsKeywordsPredicate predicate =
                new CompanyContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withCompany("Alpha").build()));

        // Non-matching keyword
        predicate = new CompanyContainsKeywordsPredicate(Arrays.asList("Charlie"));
        assertFalse(predicate.test(new PersonBuilder().withCompany("Alpha").build()));

        // Keywords match other fields but not company
        predicate = new CompanyContainsKeywordsPredicate(Arrays.asList("84588016", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withCompany("Bravo")
                .withPhone("84588016").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        CompanyContainsKeywordsPredicate predicate = new CompanyContainsKeywordsPredicate(keywords);

        String expected = CompanyContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
