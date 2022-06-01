package tourGuide.helper;

/**
 * Sets the number of users for the tests
 */
public class InternalTestHelper {

    /**
     * The number of users that will be generated
     */
    // Set this default up to 100,000 for testing
    private static int internalUserNumber = 100;

    /**
     * Gets the number of users
     *
     * @return The number of users
     */
    public static int getInternalUserNumber() {
        return internalUserNumber;
    }

    /**
     * Sets the number of users
     *
     * @param internalUserNumber : The number of users to be created
     */
    public static void setInternalUserNumber(int internalUserNumber) {
        InternalTestHelper.internalUserNumber = internalUserNumber;
    }
}
