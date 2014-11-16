package com.nilhcem.inaccessible.memory.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nilhcem.inaccessible.memory.R;
import com.nilhcem.inaccessible.memory.ui.base.BaseActivity;
import com.nilhcem.inaccessible.memory.ui.game.GameActivity;

public class MenuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
    }

    public void onStartGameSelected(View view) {
        int nbCards = Integer.parseInt((String) view.getTag());
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameActivity.EXTRA_NB_CARDS, nbCards);
        startActivity(intent);
    }
}
