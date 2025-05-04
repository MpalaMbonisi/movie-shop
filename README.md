# Movie Shop App

A full-stack web application that simulates an online movie store.

---

## 🛠 Tech Stack

- **Backend:** Java, Spring Boot, Spring Security, JPA, H2 Database, REST API
- **Frontend:** React, CSS
- **Testing:** Postman (for API), Unit tests (Service layer), Integration tests (Controller layer)

---

## 📄 Description

This project simulates an online shop for movies, built as a learning project and portfolio piece. The backend is developed using **Spring Boot** and connects to an in-memory **H2** database. The REST API is secured using **Spring Security**, so authentication is required to access most endpoints.

The frontend is built with **React**, and styled using basic **CSS**. It’s responsive and tested on mobile devices.

---

## 🔐 Authentication (Postman Setup)

When testing the API in **Postman**, keep the following steps in mind:

1. Navigate to the `Account` folder in the collection.
2. Use the `Register` and `Authenticate` requests to create an account and log in.
3. After a successful login, copy the **JWT token** from the response headers.
4. Paste the token into the Postman collection variable named `token`.
5. Now you can access protected endpoints.

> ⚠️ The token expires every 2 hours, so you'll need to refresh it as needed.

Only movie-related endpoints are publicly accessible. All others require authentication.

---

## 💡 Development Notes

The frontend was my first hands-on exploration of React. I designed the initial HTML/CSS layouts and used **OpenAI** and **GitHub Copilot** to help convert them into functional React components. It was a great way to learn and apply React in a real-world project.

Some HTTP request methods were intentionally skipped in the REST API, as they didn’t align with the intended frontend use cases.

---

## ✅ Testing

- **Service Layer:** Unit tested
- **Controller Layer:** Integration tested
- **Frontend:** Responsive design tested on various devices

---

## 🙏 Acknowledgements

- **OpenAI & GitHub Copilot** – for React guidance and code suggestions
- **Jonas Schmedtmann** – for his excellent [React course on Udemy](https://www.udemy.com/course/react-the-complete-guide-incl-redux/)

---

## Author

**Mbonisi Mpala**
