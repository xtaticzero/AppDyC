package mx.gob.sat.siat.dyc.util.common;

public class ItemComboBean
{
    private Integer id;
    private String descripcion;
    private Long idLong;
    private String idStr;

    private ItemComboBean itemPadre;

    public ItemComboBean()
    {
    
    }

    public ItemComboBean(Integer i, String d)
    {
        this.id = i;
        this.descripcion = d;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getIdLong() {
        return idLong;
    }

    public void setIdLong(Long idLong) {
        this.idLong = idLong;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public ItemComboBean getItemPadre() {
        return itemPadre;
    }

    public void setItemPadre(ItemComboBean itemPadre) {
        this.itemPadre = itemPadre;
    }
}
