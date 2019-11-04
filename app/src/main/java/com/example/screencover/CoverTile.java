package com.example.screencover;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.Log;
import android.widget.Toast;

public class CoverTile extends TileService {
    @Override
    public void onClick() {
        super.onClick();
        // called when the user click the tile

        Tile tile = getQsTile();

        boolean isActive = (tile.getState() == Tile.STATE_ACTIVE);
        if (isActive){
            tile.setState(Tile.STATE_INACTIVE);
            startActivityAndCollapse(new Intent(this, MainActivity.class));
        }else{
            tile.setState(Tile.STATE_ACTIVE);
        }

        tile.updateTile();
    }



    @Override
    public void onTileRemoved() {
        super.onTileRemoved();
        // do something when the user removes the tile
    }

    @Override
    public void onTileAdded() {
        super.onTileAdded();
        // do something when the user add the tile

        getQsTile().setState(Tile.STATE_INACTIVE);

        getQsTile().updateTile();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        // called when the tile becomes visible

    }

    @Override
    public void onStopListening() {
        super.onStopListening();
        // called when the tile is no longer visible
    }
}
