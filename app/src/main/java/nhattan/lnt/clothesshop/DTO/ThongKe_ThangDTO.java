package nhattan.lnt.clothesshop.DTO;

public class ThongKe_ThangDTO {

    String month;
    int sales;

    public ThongKe_ThangDTO(String month, int sales) {
        this.month = month;
        this.sales = sales;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }
}
