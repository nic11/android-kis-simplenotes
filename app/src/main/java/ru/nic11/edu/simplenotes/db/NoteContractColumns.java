package ru.nic11.edu.simplenotes.db;

import android.provider.BaseColumns;

public interface NoteContractColumns extends BaseColumns {
    String DATE = "date";
    String TEXT = "text";
    String TITLE = "title";
    String DRAWABLE_ID = "drawable_id_res";
}
