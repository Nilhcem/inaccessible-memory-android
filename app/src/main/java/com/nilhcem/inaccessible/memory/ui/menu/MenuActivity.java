package com.nilhcem.inaccessible.memory.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nilhcem.inaccessible.memory.R;
import com.nilhcem.inaccessible.memory.ui.base.BaseActivity;
import com.nilhcem.inaccessible.memory.ui.game.GameActivity;

public class MenuActivity extends BaseActivity {

    private static final int REQ_GAME = 1;

    private View mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        mLayout = findViewById(R.id.main_menu_layout);
    }

    @Override
    protected View getLayoutToAnnounceMessages() {
        return mLayout;
    }

    public void onStartGameSelected(View view) {
        int nbCards = Integer.parseInt((String) view.getTag());
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameActivity.EXTRA_NB_CARDS, nbCards);
        startActivityForResult(intent, REQ_GAME);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_GAME && resultCode == RESULT_OK) {
            announceMessage(data.getStringExtra(GameActivity.EXTRA_GAMEOVER_MSG), true);
        }
    }
}
