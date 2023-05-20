package com.example.softwareeng;

public class TempTask {
        private String taskName;
        private String groupName;
        private String user;
        private String completeBy;
        private int tempTaskID;
        public TempTask(String taskName, String groupName, String user, String completeBy)
        {
            this.taskName = taskName;
            this.groupName = groupName;
            this.user = user;
            this.completeBy = completeBy;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public String getGroupName(){
            return groupName;
        }

        public void setGroupName(String groupName){
            this.groupName = groupName;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getCompleteBy() {
            return completeBy;
        }

        public void setCompleteBy(String completeBy){this.completeBy = completeBy;}

        public int getTempTaskID() {return tempTaskID;}

        public void setTaskID(int tempTaskID) {this.tempTaskID = tempTaskID;}

}
