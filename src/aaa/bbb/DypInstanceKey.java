package aaa.bbb;

public class DypInstanceKey {
    private String schemaName;
    private int majorVersion;
    private String instanceName;

    public DypInstanceKey(String schemaName, int majorVersion, String instanceName) {
        this.schemaName = schemaName;
        this.majorVersion = majorVersion;
        this.instanceName = instanceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        DypInstanceKey that = (DypInstanceKey) o;

        if (majorVersion != that.majorVersion)
            return false;
        if (schemaName != null ? !schemaName.equals(that.schemaName) : that.schemaName != null)
            return false;
        return instanceName != null ? instanceName.equals(that.instanceName) : that.instanceName == null;
    }

    @Override
    public int hashCode() {
        int result = schemaName != null ? schemaName.hashCode() : 0;
        result = 31 * result + majorVersion;
        result = 31 * result + (instanceName != null ? instanceName.hashCode() : 0);
        return result;
    }

    public static void main(String[] args) {
        DypInstanceKey key1 = new DypInstanceKey("schema1", 4, "instance1");
        DypInstanceKey key2 = new DypInstanceKey("schema1", 4, "instance2");

        System.out.println(key1.hashCode());
        System.out.println(key2.hashCode());
    }
}
