#MINI-SPLITWISE
💸 MINI SPLITWISE – Expense Sharing Web Application

📌 Overview

Mini Splitwise is a web-based expense sharing application that helps users split expenses among friends using simple and intuitive methods.

The project supports both Equal Split and Dutch (Itemized) Split, along with tax, tip calculation, and payment status tracking.

The application is primarily frontend-driven, with basic backend setup for API structure.

---

🚀 Features

👥 Friend Management

- Add friends dynamically
- Remove friends easily
- Display friends in sidebar
- Used across all calculations

---

⚖️ Equal Split

- Enter total expense amount
- Automatically divides among all friends
- Displays how much each person owes
- Instant calculation

---

🍔 Dutch Split (Itemized)

- Add items in format:
  Item:Price:User1,User2
- Supports:
  - Tax %
  - Tip %
- Calculates individual contributions based on selected users
- Ensures fair distribution

---

💳 Payment Status

- Each friend has:
  - Paid / Unpaid status
- Displayed next to names
- Click to toggle status
- Compact UI design

---

📊 Dashboard

- Total Expense
- Total Users
- Total Items
- Updates dynamically

---

🎨 UI & Design

- Sidebar layout for friend management
- Equal Split and Dutch Split side-by-side
- Card-based layout
- Gradient background
- Logo integration
- Watermark-style background

---

⚠️ Validations

- Prevents calculation without adding friends
- Prevents Dutch split without items
- Displays alert messages for invalid actions

---

🛠️ Tech Stack

Frontend

- HTML5
- CSS3
- JavaScript

Backend 

- Java
- Spring Boot

✔ Backend includes:

- Project structure using Maven
- Basic controller setup
- Configuration files ("pom.xml", resources)
- Ready for API integration

---

🧠 Core Logic

Equal Split

Each Share = Total Amount / Number of Friends

---

Dutch Split Logic

1. Parse item input
2. Calculate item-wise contribution
3. Apply tax and tip
4. Distribute proportionally

✔ Ensures accuracy and fairness

---

📂 Project Structure

MiniSplitwise/
│
├── backend/
│   ├── src/
│   ├── pom.xml
│   ├── mvnw
│   ├── mvnw.cmd
│   └── application configuration files
│
├── frontend/
│   ├── index.html
│   ├── first.css
│   ├── script.js
│   └── logo.png
│
└── README.md

---

🛠️ How to Run

Backend 

cd backend
mvn spring-boot:run

Runs on:

http://localhost:8080

---

Frontend

cd frontend
open index.html

---

🎥 Demo Vedio

👉 https://drive.google.com/file/d/13TQZuRCkaZMtik93XonFBEtXmS-VyRnn/view?usp=drive_link

---

📈 Future Improvements

- Connect frontend with backend APIs
- Add database integration
- Add user authentication
- Improve UI responsiveness
- Implement settlement logic (who pays whom)

---
