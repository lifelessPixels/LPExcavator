package pl.lifelesspixels.lpexcavator.workers;

import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import pl.lifelesspixels.lpexcavator.LPExcavator;

import java.util.ArrayList;
import java.util.List;

public class OreExcavatorWorkerEffect {

    private final Material expectedMaterial;
    private final List<Block> blocksToDestroy;
    private final Player player;
    private final int tickDelay;
    private final ItemStack tool;

    public OreExcavatorWorkerEffect(Material expectedMaterial, List<Block> blocksToDestroy, Player player, int tickDelay, ItemStack tool) {
        this.expectedMaterial = expectedMaterial;
        this.blocksToDestroy = blocksToDestroy;
        this.player = player;
        this.tickDelay = tickDelay;
        this.tool = tool;
    }

    public void run() {
        // run effect task
        new BukkitRunnable() {
            private final List<Block> blocksToBreak = new ArrayList<>(blocksToDestroy);
            private final Player thePlayer = player;
            private final Note.Tone[] tones = Note.Tone.values();
            private int currentTone = 0;

            @Override
            public void run() {
                if(blocksToBreak.size() == 0) {
                    // cancel task
                    this.cancel();
                } else {
                    // get ore block to remove
                    Block block = blocksToBreak.remove(0);
                    if(expectedMaterial == block.getType()) {
                        // break ore block
                        block.breakNaturally(tool);

                        // play sound
                        if (thePlayer.isOnline()) {
                            Note note = Note.natural(0, tones[currentTone]);
                            currentTone++;
                            currentTone %= tones.length;

                            thePlayer.playNote(block.getLocation(), Instrument.STICKS, note);
                        }
                    }
                }
            }

        }.runTaskTimer(LPExcavator.getInstance(), tickDelay, tickDelay);
    }

}
