import { QinLabel, QinPanel } from "qin_case";

class Scaffold extends QinPanel {
    public constructor() {
        super();
        this.addChild(new QinLabel("Scaffold"));
    }
}

new Scaffold().putAsBody();
