package thakare.xyz.uploadpicturetutorial;

import android.graphics.Bitmap;

/**
 * Created by akshaythakare on 15/12/15.
 */
public interface GetUserCallback {
    public abstract void flagged(boolean flag);
    public abstract void imgData(Bitmap image);
}
