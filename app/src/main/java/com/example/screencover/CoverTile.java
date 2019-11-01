package com.example.screencover;

import android.graphics.drawable.Icon;
import android.service.quicksettings.Tile;
import android.util.Log;
import android.widget.Toast;

public class TileService extends android.service.quicksettings.TileService {
    @Override
    public void onClick() {
        super.onClick();
        // called when the user click the tile
        Log.d("Debug","Hello");
        System.out.println("Hello");

        Toast toast = Toast.makeText(getApplicationContext(),
                "This is a message displayed in a Toast",
                Toast.LENGTH_SHORT);

        toast.show();

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
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        // called when the tile becomes visible

        Tile t = getQsTile();
        t.setLabel("New Label");
        t.setState(Tile.STATE_ACTIVE);
        t.setIcon(Icon.createWithResource(this, R.drawable.ic_fiber_smart_record_black_24dp));
        t.updateTile();
    }

    @Override
    public void onStopListening() {
        super.onStopListening();
        // called when the tile is no longer visible
    }
}
