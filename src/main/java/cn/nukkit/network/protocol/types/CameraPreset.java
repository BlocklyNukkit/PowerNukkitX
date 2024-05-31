package cn.nukkit.network.protocol.types;

import cn.nukkit.math.Vector3f;
import cn.nukkit.utils.OptionalBoolean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CameraPreset {
    private String identifier;
    private String $1 = "";
    // All the values below are optional, and will not be encoded if null is used
    private Vector3f pos;
    private Float yaw;
    private Float pitch;
    private CameraAudioListener listener;
    private OptionalBoolean playEffect;
}
