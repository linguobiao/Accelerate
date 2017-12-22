package com.lgb.accelerate.bean;

/**
 * Created by linguobiao on 16/8/30.
 */
public class Friend implements Comparable<Friend>{
    private String name;
    private String id;
    private int steps;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int compareTo(Friend friend) {
//        int cha=this.steps-friend.steps;
//        if(cha!=0){
//            return cha;
//        }else{
//            return this.name.compareTo(friend.name);
//        }
        if (this.steps < friend.getSteps()) {
            return 1;
        } else {
            return -1;
        }
    }

//    class Student implements Comparable<Student>{
//        private String name;
//        private int age;
//        public Student(String name,int age){
//            this.name=name;
//            this.age=age;
//        }
//        public int compareTo(Student s) {
//            int cha=this.age-s.age;
//            if(cha!=0){
//                return cha;
//            }else{
//                return this.name.compareTo(s.name);
//            }
//        }
//        /* public boolean equals(Object obj){
//             if(!(obj instanceof Student)) return false;
//             Student st=(Student)obj;
//             return (age==st.age&&name.equals(st.name));
//         }
//         public int hashCode(){
//             int result=17;
//             result=37*result+age;
//             result=37*result+name.hashCode();
//             return result;
//
//       }*/
//        public String toString(){
//            return "name:"+name+"      age:"+age;
//        }
//    }

    @Override
    public String toString() {
        return "Friend{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", steps=" + steps +
                '}';
    }
}
