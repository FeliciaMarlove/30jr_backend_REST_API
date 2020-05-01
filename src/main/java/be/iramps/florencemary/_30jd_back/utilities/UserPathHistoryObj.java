package be.iramps.florencemary._30jd_back.utilities;

import java.time.LocalDate;

public class UserPathHistoryObj {
    private String pathName;
    private LocalDate dateEnd;

    public UserPathHistoryObj(String pathName, LocalDate dateEnd) {
        this.pathName = pathName;
        this.dateEnd = dateEnd;
    }

    public UserPathHistoryObj() {
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Override
    public String toString() {
        return "UserPathHistoryObj{" +
                "pathName='" + pathName + '\'' +
                ", dateEnd=" + dateEnd +
                '}';
    }
}
