package be.iramps.florencemary._30jd_back.DTO;

/**
 * DTO POST relation utilisateur-parcours
 */
public class UserPathPost implements DTOEntity {
    private Integer pathId;
    private Integer userId;

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

    public UserPathPost(Integer pathId, Integer userId) {
        this.pathId = pathId;
        this.userId = userId;
    }

    public UserPathPost() {
    }
}
