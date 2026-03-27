let friends = [];

// ADD FRIEND
function addFriend() {
    const name = document.getElementById("friendInput").value.trim();
    if (!name) return;

    friends.push({ name, paid: false });
    document.getElementById("friendInput").value = "";

    renderFriends();
    updateDashboard();
}

// REMOVE
function removeFriend(i) {
    friends.splice(i, 1);
    renderFriends();
    updateDashboard();
}

// SHOW FRIENDS
function renderFriends() {
    const list = document.getElementById("friendsList");
    list.innerHTML = "";

    friends.forEach((f, i) => {
        list.innerHTML += `<span>${f.name} <button onclick="removeFriend(${i})">x</button></span>`;
    });
}

// DASHBOARD
function updateDashboard(total = 0, items = 0) {
    document.getElementById("totalExpense").innerText = "₹" + total;
    document.getElementById("totalUsers").innerText = friends.length;
    document.getElementById("totalItems").innerText = items;
}

// EQUAL
function splitEqual() {
    const total = parseFloat(document.getElementById("total").value);

    if (!total || friends.length === 0) {
        alert("Add friends and amount");
        return;
    }

    const share = total / friends.length;

    let output = "";
    friends.forEach(f => {
        output += `${f.name} owes ₹${share.toFixed(2)}\n`;
    });

    document.getElementById("equalResult").innerText = output;

    updateDashboard(total, 0);
    renderStatus("equalStatus");
}

// DUTCH
function splitDutch() {

    const text = document.getElementById("items").value.trim();
    const tax = parseFloat(document.getElementById("tax").value) || 0;
    const tip = parseFloat(document.getElementById("tip").value) || 0;

    if (!text) {
        alert("Enter items");
        return;
    }

    let result = {};
    let total = 0;

    text.split("\n").forEach(line => {
        const [name, price, users] = line.split(":");
        const p = parseFloat(price);
        const people = users.split(",");

        total += p;
        const share = p / people.length;

        people.forEach(u => {
            if (!result[u]) result[u] = 0;
            result[u] += share;
        });
    });

    const extra = total * ((tax + tip) / 100);

    Object.keys(result).forEach(u => {
        result[u] += (result[u] / total) * extra;
    });

    let output = "";
    Object.entries(result).forEach(([u, amt]) => {
        output += `${u} owes ₹${amt.toFixed(2)}\n`;
    });

    document.getElementById("dutchResult").innerText = output;

    updateDashboard(total, Object.keys(result).length);
    renderStatus("dutchStatus");
}

// 🔥 COMPACT STATUS
function renderStatus(containerId) {

    const box = document.getElementById(containerId);
    box.innerHTML = "";

    friends.forEach((f, i) => {

        box.innerHTML += `
        <div class="status-row">
            <span>${f.name}</span>

            <button 
                class="status-btn ${f.paid ? 'paid' : 'unpaid'}"
                onclick="togglePaid(${i}, '${containerId}')">
                ${f.paid ? '✓ Paid' : '✗ Unpaid'}
            </button>
        </div>
        `;
    });
}

// TOGGLE
function togglePaid(i, containerId) {
    friends[i].paid = !friends[i].paid;
    renderStatus(containerId);
}