package com.example.medicalservice.tools;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class MyMigration extends Migration {
    /**
     * Creates a new migration between {@code startVersion} and {@code endVersion}.
     *
     * @param startVersion The start version of the database.
     * @param endVersion   The end version of the database after this migration is applied.
     */
    public MyMigration(int startVersion, int endVersion) {
        super(startVersion, endVersion);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {

        if(database.getVersion() == 2){

            database.execSQL("alter table UserBean add column nickName text");
        }

    }
}
