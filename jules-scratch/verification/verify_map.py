from playwright.sync_api import sync_playwright, expect

def run(playwright):
    browser = playwright.chromium.launch(headless=True)
    context = browser.new_context()
    page = context.new_page()

    try:
        page.goto("http://localhost:8000/frontend/", timeout=30000)

        # Wait for the map canvas to be visible
        map_canvas = page.locator("#map canvas")
        expect(map_canvas).to_be_visible(timeout=15000)

        # Give the map tiles a moment to load
        page.wait_for_timeout(5000)

        page.screenshot(path="jules-scratch/verification/verification.png")
        print("Screenshot taken successfully.")

    except Exception as e:
        print(f"An error occurred: {e}")
        page.screenshot(path="jules-scratch/verification/error.png")
    finally:
        browser.close()

with sync_playwright() as playwright:
    run(playwright)