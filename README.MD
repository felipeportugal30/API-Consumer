# 📈 Consumer API - Crypto Data Visualizer

This project consumes the [CoinMarketCap API](https://coinmarketcap.com/api/) and generates visual dashboards with cryptocurrency data.

> ⚠️ It uses the **free plan** of the API, so rate limits apply (see below).

---

## 🚀 Technologies Used

- Java (Spring Boot)
- RestTemplate for HTTP requests
- PostgreSQL (or your DB)
- Scheduled tasks (with `@Scheduled`)
- CoinMarketCap API
- [Optional] Front-end for visualizations (React, etc.)

---

## 🗂️ Project Structure

The codebase follows a layered architecture:
/src
├── config # RestTemplate configuration
├── controller # Handles HTTP requests (minimal use)
├── dto # Data Transfer Objects
├── model # Database models for cryptocurrencies
├── repository # JPA interfaces to query the DB
├── scheduler # Triggers API calls every 5 minutes
├── service # Main business logic

---

## 🔁 API Usage and Rate Limits

This project uses the **free tier** of CoinMarketCap:

- 10,000 credits/month
- 333 credits/day
- 30 credits/minute
- 9 available endpoints
- Personal use only

> To stay within limits, this app makes **1 API call every 5 minutes**.

---

## 📦 How to Run

1. Clone this repo  
2. Add your API key in `application.properties`:
    ```properties
    coinmarketcap.api.key=YOUR_API_KEY_HERE
    ```
3. Run the application with Maven/Gradle
4. Watch the logs or connect to the DB to view data updates

---

## 📊 Future Improvements (optional)
- Front-end dashboard (React + Recharts/D3.js)
- Docker containerization
- CI/CD pipeline
- Error handling and retries

---

## 📝 License

This project is for **educational/personal** use only due to API terms.
