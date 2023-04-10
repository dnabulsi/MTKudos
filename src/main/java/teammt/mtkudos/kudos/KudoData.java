package teammt.mtkudos.kudos;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.mlib.main.MLib;

@Data
@NoArgsConstructor
public class KudoData {
    private UUID player;
    private ItemStack skull;
    private long lastGiven = 0;
    private int kudosGiven = 0;
    private int kudosReceived = 0;

    private Set<String> rewardsClaimed = new HashSet<>();

    public KudoData(UUID player, MLib lib) {
        this.player = player;
        this.skull = getSkull(player, lib);
    }

    public void incrementKudosGiven() {
        kudosGiven++;
    }

    public void incrementKudosReceived() {
        kudosReceived++;
    }

    public void save(MLib lib) {
        lib.getConfigurationAPI().getConfig("data").set("data." + player, this);
    }

    @SuppressWarnings("deprecation")
    private ItemStack getSkull(UUID uuid, MLib lib) {
        Player player = Bukkit.getPlayer(uuid);
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
        if (lib.getCompatibilityApi().getServerVersion().getMajor() >= 13)
            skullMeta.setOwningPlayer(player);
        else
            skullMeta.setOwner(player.getName());
        playerHead.setItemMeta(skullMeta);
        return playerHead;
    }
}