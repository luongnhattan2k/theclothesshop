package nhattan.lnt.clothesshop.DTO;

public class CategoryDTO {
    private String name;
    private String IDDANHMUC;

    public CategoryDTO(String name, String IDDANHMUC) {
        this.name = name;
        this.IDDANHMUC = IDDANHMUC;
    }

    public String getIDDANHMUC() {
        return IDDANHMUC;
    }

    public void setIDDANHMUC(String IDDANHMUC) {
        this.IDDANHMUC = IDDANHMUC;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
