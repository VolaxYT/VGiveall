package fr.volax.vgiveall.items;

import lombok.Data;
import org.bukkit.inventory.ItemStack;


@Data
public class Item {
    private ItemStack itemStack;
    private String adminName, adminID, date;

    public static class Builder {
        private ItemStack itemStack;
        private String adminName, adminID, date;

        public Builder withItemStack(ItemStack itemStack){
            this.itemStack = itemStack;
            return this;
        }

        public Builder withAdminName(String adminName){
            this.adminName = adminName;
            return this;
        }

        public Builder withAdminID(String adminID){
            this.adminID = adminID;
            return this;
        }

        public Builder withDate(String date){
            this.date = date;
            return this;
        }

        public Item build(){
            Item item = new Item();
            item.setItemStack(itemStack);
            item.setAdminID(adminID);
            item.setAdminName(adminName);
            item.setDate(date);

            return item;
        }
    }
}
