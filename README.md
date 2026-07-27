# PageSight

PageSight is a web monitoring system that makes tracking website changes simple. Instead of manually finding and maintaining CSS selectors, users can visually select any element on a webpage with a single click. PageSight automatically monitors the selected content and notifies users whenever changes are detected.

The application provides a centralized dashboard for managing all monitors, viewing notifications, and tracking monitoring activity.

---

## Objectives

- Eliminate the need for manual CSS selector configuration.
- Provide an intuitive visual interface for selecting elements to monitor.
- Notify users whenever a monitored webpage or element changes.
- Centralize all monitored pages through a single dashboard.
- Track monitoring activity with usage statistics such as the total number of checks performed.

---

## Features

### Visual Selector
Select any element directly on a webpage by clicking on it. No CSS selector knowledge is required.

### Change Notifications
Receive notifications through the application whenever a monitored page or element changes.

### Dashboard
Manage all monitored websites and elements from one centralized dashboard.

### Total Checks Counter
Keep track of the total number of monitoring checks performed across all configured monitors.

### Favorites
Mark important monitors as favorites for quick access from the popup interface.

---

## Project Description

PageSight removes the complexity of traditional website monitoring by replacing manual CSS selector configuration with an interactive visual selector.

Users simply navigate to a webpage, click on the element they wish to monitor, and PageSight automatically stores the required information. The system periodically checks monitored pages for changes and generates notifications whenever updates are detected.

A centralized dashboard provides complete visibility into all active monitors, while statistics such as the total number of checks performed help users understand monitoring activity over time.

---

## Technologies Used

| Technology | Purpose |
|------------|---------|
| Spring Boot | Backend APIs, monitoring services, and application logic |
| React | Dashboard, popup interface, and visual selector |

> No external libraries beyond the core Spring Boot and React frameworks are used in this implementation.

---

## Core Functionality

- Visual element selection
- Website monitoring
- Automatic change detection
- User notifications
- Monitor management dashboard
- Favorites support
- Monitoring statistics

---

## Use Cases

- Monitor product prices
- Track documentation updates
- Watch job postings
- Follow news articles
- Monitor blog updates
- Track important webpage content changes