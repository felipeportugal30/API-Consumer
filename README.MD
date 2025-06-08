# CONSUMER API
1. About this project
    - This project have the intention to consume an API and constructs some graphics
    - I used the Coin Market Cap Api to construct this project

2. The Organization of the project
    - The project is separated in pastes: Model, Controller, Service, Config, Dto and Repository
    - Model: this folder have files that model the cryptos into the database
    - Controller: this folder call and receive calls in the endpoints (not too used)
    - Service: this folder has the logic of the calls
    - Config: this one is only to config the RestTemplate
    - Dto: to transfer data between the layers
    - Repository: allows an easier communication with database
    - Scheduler: calls services every period, in this case 5 minutes
    
3. Coin Market Cap
   - I only used the free plan
   - The free plan has some limitations, like:
     - Credits per Month: 10000
     - Credits per Day: 333
     - Credits per Minute: 30
     - 9 different endpoints
     - Only for personal use
   - If you want to use the credits evenly, I recommend using 1 credit every 5 minutes
