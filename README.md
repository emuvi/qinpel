# Qinpel

Qinpel (Quick Interface to Power Intelligence) is a comprehensive information platform that comprises various backends, frontends, libraries, utilities, and user applications.

## Subprojects

This repository contains the following subprojects, which together build the Qinpel ecosystem and the Vidlus information platform:

### Core Systems
* **[QinDesk](./qin_desk)**: The frontend of Qinpel. It acts as the manager of user applications for the Vidlus information platform.
* **[QinSunset](./qin_sunset)**: The backend of Qinpel and the base of the Vidlus information platform. It is a command program that serves public files, graphical user interfaces, file system access with authorization, command programs dispatchers and monitoring, databases queries, and scripts execution.

### Applications & Interfaces
* **[AbDesk](./qia_abdesk)**: (Abracadabra Desktop): A user interface for all the common functionalities available in QinDesk on the Qinpel platform.
* **[AdMister](./qia_admister)**: (Administration Profession): A manager with common functionalities and the launcher of user applications for the administration profession on the Qinpel platform.
* **[AdPeople](./qia_ad_people)**: A user application within the AdMister suite providing tools and information on people that a company may need.
* **[AdSales](./qia_ad_sales)**: A user application within the AdMister suite providing tools and information on sales that a company may need.

### Libraries
* **[QinSoul](./qin_soul)**: The core library for user applications, providing a wide set of functionalities available on Qinpel.
* **[QinCase](./qin_case)**: The UI library for user applications, offering a wide set of graphical components available on Qinpel.

### Utilities
* **[PGBack](./qir_pgback)**: A collection of command scripts that manages PostgreSQL database backups, global object preservation, and automated system restorations. It serves as a utility of Qinpel.
