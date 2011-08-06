/*
 * Copyright (c) Mattia Barbon <mattia@barbon.org>
 * distributed under the terms of the MIT license
 */

package org.barbon.acash;

import org.barbon.acash.compat.CompatListActivity;

import android.content.Intent;

import android.os.Bundle;
import android.os.Environment;

import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileFilter;

public class BrowseGnuCash extends CompatListActivity {
    public static final String FILE_NAME = "fileName";

    private class FileSystemAdapter extends BaseAdapter {
        private int itemLayout;
        private File currentPath;
        private FileFilter fileFilter;
        private File[] files;

        public FileSystemAdapter(int layout_id) {
            super();

            itemLayout = layout_id;
        }

        public void setFileFilter(FileFilter filter) {
            fileFilter = filter;

            refresh();
        }

        public void setCurrentDirectory(File currentDirectory) {
            currentPath = currentDirectory;

            refresh();
        }

        public File getCurrentDirectory() {
            return currentPath;
        }

        public File getFile(int index) {
            return files[index];
        }

        // adapter methods

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            File fileName = files[position];

            if (convertView == null)
                convertView = getLayoutInflater().inflate(itemLayout, null);

            TextView fileNameView =
                (TextView) convertView.findViewById(R.id.file_name);

            fileNameView.setText(fileName.getName());

            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getCount() {
            return files.length;
        }

        @Override
        public Object getItem(int position) {
            return files[position];
        }

        // implementation

        private void refresh() {
            if (fileFilter != null)
                files = currentPath.listFiles(fileFilter);
            else
                files = currentPath.listFiles();

            notifyDataSetChanged();
        }
    }

    private static class Filter implements FileFilter {
        public boolean accept(File file) {
            String name = file.getName();

            // accept directories and files ending with .gnucash;
            // ignore .files

            if (name.charAt(0) == '.')
                return false;

            if (file.isDirectory())
                return true;

            if (name.endsWith(".gnucash"))
                return true;

            return false;
        }
    }

    private FileSystemAdapter fileAdapter;
    private File rootDirectory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootDirectory = Environment.getExternalStorageDirectory();
        fileAdapter = new FileSystemAdapter(R.layout.fileitem);
        fileAdapter.setCurrentDirectory(rootDirectory);
        fileAdapter.setFileFilter(new Filter());

        setListAdapter(fileAdapter);
    }

    // event handlers

    @Override
    public void onBackPressed() {
        File currentDirectory = fileAdapter.getCurrentDirectory();

        if (currentDirectory.equals(rootDirectory))
            super.onBackPressed();
        else
            fileAdapter.setCurrentDirectory(currentDirectory.getParentFile());
    }

    @Override
    protected void onListItemClick(ListView list, View v,
                                   int position, long id) {
        File clickedFile = fileAdapter.getFile(position);

        if (clickedFile.isDirectory()) {
            fileAdapter.setCurrentDirectory(clickedFile);

            return;
        }
        else {
            Intent result = new Intent();

            result.putExtra(FILE_NAME, clickedFile.getAbsolutePath());

            setResult(RESULT_OK, result);
            finish();
        }
    }
}
