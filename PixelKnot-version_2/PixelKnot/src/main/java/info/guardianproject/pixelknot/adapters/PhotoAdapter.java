package info.guardianproject.pixelknot.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import info.guardianproject.pixelknot.R;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoViewHolder> {

    public interface PhotoAdapterListener {
        void onPhotoSelected(String photo, View thumbView);
        void onCameraSelected();
        void onAlbumsSelected();
    }

    private final Context mContext;
    private final String mAlbum;
    private final ArrayList<PhotoInfo> mPhotos;
    private final boolean mShowCamera;
    private final boolean mShowAlbums;
    private PhotoAdapterListener mListener;

    public PhotoAdapter(Context context, String album, boolean showCamera, boolean showAlbums) {
        super();
        mContext = context;
        mAlbum = album;
        mPhotos = new ArrayList<>();
        mShowCamera = showCamera;
        mShowAlbums = showAlbums;
        getPhotos();
    }

    public void update() {
        getPhotos();
        notifyDataSetChanged();
    }

    public void setListener(PhotoAdapterListener listener) {
        mListener = listener;
    }

    private void getPhotos() {
        mPhotos.clear();
        try {
            final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
            String searchParams = null;
            if (!TextUtils.isEmpty(mAlbum)) {
                searchParams = MediaStore.Images.Media.BUCKET_ID + " = \"" + mAlbum + "\"";
            }

            Cursor photoCursor = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null, searchParams, null, orderBy + " DESC");

            if (photoCursor.moveToFirst()) {
                int dataColumn = photoCursor.getColumnIndex(MediaStore.Images.Media.DATA);
                do {
                    PhotoInfo photo = new PhotoInfo();
                    photo.thumbnail = photoCursor.getString(dataColumn);
                    mPhotos.add(photo);
                } while (photoCursor.moveToNext());
            }
            photoCursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(viewType == 0 ? R.layout.photo_item : R.layout.photo_camera_item, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (mShowCamera) {
            if (position == 0)
                return 1;
            else
                position--;
        }
        if (mShowAlbums) {
            if (position == 0)
                return 2;
            else
                position--;
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        if (mShowCamera) {
            if (position == 0) {
                holder.mRootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mListener != null) {
                            mListener.onCameraSelected();
                        }
                    }
                });
                return;
            } else {
                position--; //Offset by this item
            }
        }
        if (mShowAlbums) {
            if (position == 0) {
                holder.mPhoto.setImageResource(R.drawable.ic_photo_album_primary_36dp);
                holder.mRootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mListener != null) {
                            mListener.onAlbumsSelected();
                        }
                    }
                });
                return;
            } else {
                position--; //Offset by this item
            }
        }
        PhotoInfo photo = mPhotos.get(position);
        holder.mPhoto.setBackgroundColor(Color.TRANSPARENT);
        holder.mRootView.setOnClickListener(new ItemClickListener(position, holder.mPhoto));
        try {
            Picasso.with(mContext)
                    .load(new File(photo.thumbnail))
                    .fit()
                    .centerCrop()
                    .into(holder.mPhoto);
        } catch (Exception ignored) {}
    }

    @Override
    public int getItemCount() {
        return (mShowCamera ? 1 : 0) + (mShowAlbums ? 1 : 0) + mPhotos.size();
    }

    private class PhotoInfo {
        public PhotoInfo() {
        }
        public String thumbnail;
    }

    private class ItemClickListener implements View.OnClickListener {
        private final int mPosition;
        private final View mThumbView;

        public ItemClickListener(int position, View thumbView) {
            mPosition = position;
            mThumbView = thumbView;
        }

        @Override
        public void onClick(View view) {
            PhotoInfo photo = mPhotos.get(mPosition);
            if (mListener != null) {
                mListener.onPhotoSelected(photo.thumbnail, mThumbView);
            }
        }
    }
}

