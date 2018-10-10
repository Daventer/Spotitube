package nl.han.dea.dave.controllers.dto;

public class TestDTO {
    private String name;

    public TestDTO(String name){
        this.name = name;
    }

    public TestDTO(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
