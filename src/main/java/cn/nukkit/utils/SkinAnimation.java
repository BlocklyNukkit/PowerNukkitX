package cn.nukkit.utils;

import cn.nukkit.api.DeprecationDetails;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class SkinAnimation {
    public final SerializedImage image;
    public final int type;
    public final float frames;

    public final int expression;

    @Deprecated
    @DeprecationDetails(since = "1.4.0.0-PN",
            reason = "The expression field was added and the constructor's signature was changed",
            replaceWith = "SkinAnimation(SerializedImage image, int type, float frames, int expression)")
    public SkinAnimation(SerializedImage image, int type, float frames) {
        this(image, type, frames, 0);
    }

    public SkinAnimation(SerializedImage image, int type, float frames, int expression) {
        this.image = image;
        this.type = type;
        this.frames = frames;
        this.expression = expression;
    }
}
