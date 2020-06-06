package be.iramps.florencemary._30jd_back.DTO;

import be.iramps.florencemary._30jd_back.models.PK_User_Path;

/**
 * DTO GET relation utilisateur-parcours
 */
public class UserPathGet  implements DTOEntity {
    private PK_User_Path pkUserPath;
    private boolean ongoing;
    private Integer pathId;
    private Integer userId;

    public PK_User_Path getPkUserPath() {
        return pkUserPath;
    }

    public void setPkUserPath(PK_User_Path pkUserPath) {
        this.pkUserPath = pkUserPath;
    }

    public boolean isOngoing() {
        return ongoing;
    }

    public void setOngoing(boolean ongoing) {
        this.ongoing = ongoing;
    }

    public Integer getPathId() {
        return pathId;
    }

    public void setPathId(Integer pathId) {
        this.pathId = pathId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public UserPathGet(PK_User_Path pkUserPath, boolean ongoing, Integer pathId, Integer userId) {
        this.pkUserPath = pkUserPath;
        this.ongoing = ongoing;
        this.pathId = pathId;
        this.userId = userId;
    }

    public UserPathGet() {
    }

    @Override
    public String toString() {
        return "UserPathGet{" +
                "pkUserPath=" + pkUserPath +
                ", ongoing=" + ongoing +
                ", pathId=" + pathId +
                ", userId=" + userId +
                '}';
    }
}
