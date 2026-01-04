# Base image for the Angular frontend
FROM nginx:alpine

# Copy built Angular app to Nginx html directory
COPY dist/frontend-angular/browser /usr/share/nginx/html

# Copy custom Nginx configuration
COPY nginx.conf /etc/nginx/nginx.conf

# Expose port 80
EXPOSE 80