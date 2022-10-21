package me.bega.gem;

public class Gem implements Comparable<Gem>{
    private String id;
    private String name;
    private boolean preciousness;
    private String origin;
    private double value;
    private VisualParameters visualParameters;

    public Gem() {
        this.id = "";
        this.name = "";
        this.preciousness = false;
        this.origin = "";
        this.visualParameters = new VisualParameters();
        this.value = 0;
    }
    public Gem(String id, String name, boolean preciousness, String origin, VisualParameters visualParameters, double value) {
        this.id = id;
        this.name = name;
        this.preciousness = preciousness;
        this.origin = origin;
        this.visualParameters = visualParameters;
        this.value = value;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public boolean isPreciousness() {
        return preciousness;
    }
    public String getOrigin() {
        return origin;
    }
    public VisualParameters getVisualParameters() {
        return visualParameters;
    }
    public double getValue() {
        return value;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPreciousness(boolean preciousness) {
        this.preciousness = preciousness;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    public void setVisualParameters(VisualParameters visualParameters) {
        this.visualParameters = visualParameters;
    }
    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public int compareTo(Gem o) {
        return name.compareTo(((Gem) o).getName());
    }

    @Override
    public String toString() {
        return "Gem{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", preciousness=" + preciousness +
                ", origin='" + origin + '\'' +
                ", value=" + value +
                ", visualParameters=" + visualParameters +
                '}' + '\n';
    }
}
