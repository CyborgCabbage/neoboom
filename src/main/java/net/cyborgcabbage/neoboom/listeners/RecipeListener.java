package net.cyborgcabbage.neoboom.listeners;

import net.minecraft.block.BlockBase;
import net.minecraft.entity.Item;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationloader.api.common.event.recipe.RecipeRegister;
import net.modificationstation.stationloader.api.common.recipe.CraftingRegistry;

public class RecipeListener implements RecipeRegister {
    @Override
    public void registerRecipes(String recipeType) {
        RecipeRegister.Vanilla type = RecipeRegister.Vanilla.fromType(recipeType);
        if (type == RecipeRegister.Vanilla.CRAFTING_SHAPED) {
            addBombTierRecipes(BlockBase.TNT,BlockListener.getBlock("normal_bomb"));
            addBombTierRecipes(BlockListener.getBlock("compressed_bomb"),BlockListener.getBlock("compressed_bomb"));
            addBombTierRecipes(BlockListener.getBlock("nuclear_bomb"),BlockListener.getBlock("nuclear_bomb"));
        }

        CraftingRegistry.INSTANCE.addShapedRecipe( new ItemInstance(BlockListener.getBlock("compressed_bomb"),1,0),
                " t ",
                "tst",
                " t ",
                't', new ItemInstance(BlockBase.TNT),
                's', new ItemInstance(ItemBase.string)
        );

        CraftingRegistry.INSTANCE.addShapedRecipe( new ItemInstance(BlockListener.getBlock("nuclear_bomb"),1,0),
                " t ",
                "tdt",
                " t ",
                't', new ItemInstance(BlockListener.getBlock("compressed_bomb")),
                'd', new ItemInstance(ItemBase.diamond)
        );
    }

    private static void addBombTierRecipes(BlockBase input, BlockBase output){
        CraftingRegistry.INSTANCE.addShapedRecipe( new ItemInstance(output,1,2),
                "ese",
                "ete",
                "ese",
                't', new ItemInstance(input),
                'e', new ItemInstance(ItemBase.seeds),
                's', new ItemInstance(BlockBase.SAPLING)
        );
        CraftingRegistry.INSTANCE.addShapedRecipe( new ItemInstance(output,1,4),
                "t",
                "o",
                't', new ItemInstance(input),
                'o', new ItemInstance(BlockBase.OBSIDIAN)
        );
        CraftingRegistry.INSTANCE.addShapedRecipe( new ItemInstance(output,1,6),
                "ggg",
                "gtg",
                "ggg",
                't', new ItemInstance(input),
                'g', new ItemInstance(BlockBase.GLOWSTONE)
        );
        CraftingRegistry.INSTANCE.addShapedRecipe( new ItemInstance(output,1,8),
                "sss",
                "sts",
                "sss",
                't', new ItemInstance(input),
                's', new ItemInstance(ItemBase.snowball)
        );
        CraftingRegistry.INSTANCE.addShapedRecipe( new ItemInstance(output,1,10),
                "lbl",
                "ltl",
                "lbl",
                't', new ItemInstance(input),
                'l', new ItemInstance(ItemBase.leather),
                'b', new ItemInstance(ItemBase.waterBucket)
        );
        CraftingRegistry.INSTANCE.addShapedRecipe( new ItemInstance(output,1,12),
                "ccc",
                "ctc",
                "ccc",
                't', new ItemInstance(input),
                'c', new ItemInstance(ItemBase.coal)
        );
        CraftingRegistry.INSTANCE.addShapedRecipe( new ItemInstance(output,1,14),
                "rir",
                "rtr",
                "rir",
                't', new ItemInstance(input),
                'r', new ItemInstance(ItemBase.redstoneDust),
                'i', new ItemInstance(ItemBase.ironIngot)
        );
    }

    private static void addSquareRecipe(ItemInstance src, ItemInstance res) {
        CraftingRegistry.INSTANCE.addShapedRecipe(res, "##", "##", '#', src);
    }

    private static void addPillarRecipe(ItemInstance src, ItemInstance res) {
        CraftingRegistry.INSTANCE.addShapedRecipe(res, "#", "#", '#', src);
    }

    private static void addStairsRecipe(BlockBase src, int meta, BlockBase res) {
        CraftingRegistry.INSTANCE.addShapedRecipe(new ItemInstance(res, 4), "#  ", "## ", "###", '#', new ItemInstance(src, 1, meta));
    }

    private static void addSlabRecipe(BlockBase src, int meta, BlockBase res) {
        CraftingRegistry.INSTANCE.addShapedRecipe(new ItemInstance(res, 6), "###", '#', new ItemInstance(src, 1, meta));
    }

    private static void addSlabRecipe(ItemInstance src, BlockBase res) {
        CraftingRegistry.INSTANCE.addShapedRecipe(new ItemInstance(res, 6), "###", '#', src);
    }
}

