package pl.lifelesspixels.lpexcavator.workers;

import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import pl.lifelesspixels.lpexcavator.LPExcavator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TreeExcavatorWorkerEffect {

    private final Material expectedWoodMaterial;
    private final Set<Material> expectedLeavesMaterials;
    private final List<Block> woodBlocks;
    private final List<Block> leavesBlocks;
    private final Player player;
    private final int tickDelay;
    private final ItemStack tool;

    public TreeExcavatorWorkerEffect(Material expectedWoodMaterial, Set<Material> expectedLeavesMaterials,
                                     List<Block> woodBlocks, List<Block> leavesBlocks, Player player,
                                     int tickDelay, ItemStack tool) {
        this.expectedWoodMaterial = expectedWoodMaterial;
        this.expectedLeavesMaterials = expectedLeavesMaterials;
        this.woodBlocks = woodBlocks;
        this.leavesBlocks = leavesBlocks;
        this.player = player;
        this.tickDelay = tickDelay;
        this.tool = tool;
    }

    public void run() {
        // run effect task
        new BukkitRunnable() {
            private final List<Block> logsToBreak = new ArrayList<>(woodBlocks);
            private final List<Block> leavesToBreak = new ArrayList<>(leavesBlocks);
            private final Player thePlayer = player;
            private final Note.Tone[] tones = Note.Tone.values();
            private int currentTone = 0;

            @Override
            public void run() {
                if(logsToBreak.size() == 0) {
                    // play sound
                    if(thePlayer.isOnline() && leavesToBreak.size() > 0)
                        thePlayer.playSound(leavesToBreak.get(0).getLocation(), Sound.BLOCK_GRASS_BREAK, 1.0f, 1.0f);

                    // break all leaves at the same time
                    for(Block leaves : leavesToBreak) {
                        if(expectedLeavesMaterials.contains(leaves.getType()))
                            leaves.breakNaturally();
                    }

                    // cancel task
                    this.cancel();
                } else {
                    // get wood block to remove
                    Block block = logsToBreak.remove(0);
                    if(expectedWoodMaterial == block.getType()) {
                        // break wood block
                        block.breakNaturally(tool);

                        // play sound
                        if (thePlayer.isOnline()) {
                            Note note = Note.natural(1, tones[currentTone]);
                            currentTone++;
                            currentTone %= tones.length;

                            thePlayer.playNote(block.getLocation(), Instrument.BASS_DRUM, note);
                        }
                    }
                }
            }

        }.runTaskTimer(LPExcavator.getInstance(), tickDelay, tickDelay);
    }

}
