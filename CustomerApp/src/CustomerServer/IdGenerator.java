package CustomerServer;

/**
 * IdGenerator class and its instance methods and variables.
 * This class is used to generate ids and keep track of the next id to be generated.
 *
 * @author Michael Lee & Bryce Shaw
 * @version 1.0
 * @since 2020/02/08
 */

public class IdGenerator {
    private int idGen;

    /**
     * Instantiates a new Id generator.
     */
    public IdGenerator() {
        idGen = 0;
    }

    /**
     * Gets id gen.
     *
     * @return the id gen
     */
    public int getIdGen() {
        return idGen;
    }

    /**
     * Sets id gen.
     *
     * @param idGen the id gen
     */
    public void setIdGen(int idGen) {
        this.idGen = idGen;
    }
}
