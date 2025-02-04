const apiUrl = "http://localhost:8080/api/skills";  // Backend URL

// Fetch and display skills
function fetchSkills() {
    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            const skillList = document.getElementById("skillList");
            skillList.innerHTML = "";
            data.forEach(skill => {
                const li = document.createElement("li");
                li.innerHTML = `${skill.name} - ${skill.proficiencyLevel} 
                    <button class="delete" onclick="deleteSkill(${skill.id})">Delete</button>`;
                skillList.appendChild(li);
            });
        })
        .catch(error => console.error("Error fetching skills:", error));
}

// Add a new skill
document.getElementById("skillForm").addEventListener("submit", function(event) {
    event.preventDefault();

    const newSkill = {
        name: document.getElementById("name").value,
        description: document.getElementById("description").value,
        proficiencyLevel: document.getElementById("proficiencyLevel").value
    };

    fetch(apiUrl, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(newSkill)
    })
        .then(response => response.json())
        .then(() => {
            fetchSkills();
            document.getElementById("skillForm").reset();
        })
        .catch(error => console.error("Error adding skill:", error));
});

// Delete a skill
function deleteSkill(id) {
    fetch(`${apiUrl}/${id}`, { method: "DELETE" })
        .then(() => fetchSkills())
        .catch(error => console.error("Error deleting skill:", error));
}

// Load skills on page load
fetchSkills();
