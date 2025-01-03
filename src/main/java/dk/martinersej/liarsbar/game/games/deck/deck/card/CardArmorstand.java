package dk.martinersej.liarsbar.game.games.deck.deck.card;

import dk.martinersej.liarsbar.utils.MathUtils;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

public class CardArmorstand {

    public static void spawn(Location location, CardItem cardItem) {
        location = location.clone();
        location.setYaw(0);
        ArmorStand armorStand = location.getWorld().spawn(location, ArmorStand.class);
        applyTo(armorStand, cardItem);
    }

    private static void applyTo(ArmorStand armorStand, CardItem cardItem) {
        // default setup
        armorStand.setGravity(false);
        armorStand.setMarker(true);
        armorStand.setVisible(false);

        // card setup
        armorStand.setItemInHand(cardItem.getItemStack());
        EulerAngle eulerAngle = new EulerAngle(0, MathUtils.degreesToRadians(90), MathUtils.degreesToRadians(180));
        armorStand.setRightArmPose(eulerAngle);
    }

    public static void changeCardItem(ArmorStand armorStand, CardItem cardItem) {
        armorStand.setItemInHand(cardItem.getItemStack());
    }

    private static ItemStack getItemInHand(ArmorStand armorStand) {
        return armorStand.getEquipment().getItemInHand();
    }

    public static CardItem getCardItem(ArmorStand armorStand) {
        return CardItem.getCardByItemStack(getItemInHand(armorStand));
    }
}
